SUMMARY = "Quartus lite"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native"

SRC_URI = "\
	https://cdn.gowinsemi.com.cn/Gowin_V1.9.10.03_Education_linux.tar.gz \
"
SRC_URI[sha256sum] = "1cd0b9ce86897509b12f05bebd0ec2a7b193b7168c37d82676584e9211a6e2fa"

S = "${UNPACKDIR}"

inherit native

SYSROOT_DIRS_NATIVE += "${prefix}/gowin"

do_install() {
	install -d ${D}${prefix}/gowin
	cp -r ${UNPACKDIR}/* ${D}${prefix}/gowin
	rm ${D}${prefix}/gowin/IDE/lib/libfreetype.so.6
}
