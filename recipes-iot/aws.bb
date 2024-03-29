DESCRIPTION = "AWS library"
SECTION = "base"
LICENSE = "Apache-2.0"

LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=acc7a1bf87c055789657b148939e4b40"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
SRC_URI = " \
	git://github.com/aws/aws-iot-device-sdk-embedded-C.git;rev=release;branch=release;destsuffix=git/aws \
	git://github.com/ARMmbed/mbedtls.git;rev=mbedtls-2.7;branch=mbedtls-2.7;destsuffix=git/aws/external_libs/mbedTLS \
	"

do_compile:prepend() {
  echo "clean:" >> ${S}/external_libs/CppUTest/Makefile
  cp ${S}/samples/linux/subscribe_publish_library_sample/Makefile ${S}
  sed -i -e 's/..\/..\/../.\//' ${S}/Makefile
  sed -i -e 's/CC = gcc/#CC = gcc/' ${S}/Makefile
  sed -i -e 's/LOG_FLAGS += -DENABLE_IOT_DEBUG/#LOG_FLAGS += -DENABLE_IOT_DEBUG/' ${S}/Makefile
  sed -i -e 's/LOG_FLAGS += -DENABLE_IOT_INFO/#LOG_FLAGS += -DENABLE_IOT_INFO/' ${S}/Makefile
  cp ${S}/samples/linux/subscribe_publish_library_sample/aws_iot_config.h ${S}
  cp ${S}/samples/linux/subscribe_publish_library_sample/subscribe_publish_library_sample.c ${S}
}

S = "${WORKDIR}/git/aws"

#BUILD_CFLAGS += " -g -O0"

do_install:append() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}
	install -d ${D}${includedir}/aws
	install -m 0644 libAwsIotSdk.a ${D}${libdir}
	install -m 0755 ${S}/include/* ${D}${includedir}/aws
	install -m 0755 ${S}/platform/linux/common/timer_platform.h ${D}${includedir}/aws
	install -m 0755 ${S}/platform/linux/mbedtls/network_platform.h ${D}${includedir}/aws

	for filename in ${D}${includedir}/aws/*.h; do
	  sed -i -e 's/#include "aws_iot_config.h"//' $filename
	  sed -i -e 's/#include <aws_/#include <aws\/aws_/' $filename
	  sed -i -e 's/#include "aws_/#include "aws\/aws_/' $filename
#	  sed -i -e 's/#ifdef ENABLE_IOT_.\+/#if 1/' $filename
	done
}

PROVIDES += "${PN}-dev ${PN}-staticdev"

FILES:${PN}-staticdev = "${libdir}/*.a"
FILES:${PN}-dev = "${prefix}/include/aws/*.h"

inherit pkgconfig
