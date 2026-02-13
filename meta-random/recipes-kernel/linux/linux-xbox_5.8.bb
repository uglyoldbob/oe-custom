KBRANCH ?= "xbox-linux"

LINUX_KERNEL_TYPE = "tiny"
KCONFIG_MODE = "--allnoconfig"

require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION ?= "5.8.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"

SRCREV_machine ?= "cc89bd62acde4130b24854711db18c6513678484"

PV = "${LINUX_VERSION}+git${SRCPV}"

SRC_URI = "git://github.com/xboxdev/xbox-linux.git;protocol=https;branch=xbox-linux;name=machine \
 file://defconfig"
# git://github.com/uglyoldbob/kernel_bsp.git;protocol=https;branch=main;type=kmeta;name=meta;destsuffix=${KMETA};rev=941539a7143328ac1ce444fcbfbd76552bbd3fe4"
#
#           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.8;destsuffix=${KMETA}"

COMPATIBLE_MACHINE = "xbox"

# Functionality flags
KERNEL_FEATURES = ""

PACKAGES += "cromwell-kernel"
FILES:cromwell-kernel = "/boot/kernel"

do_install:append () {
 install -m 0644 ${WORKDIR}/linux-xbox-tiny-build/arch/i386/boot/bzImage ${D}/boot/kernel
 rm -f ${D}/boot/bzImage*
 rm -f ${D}/boot/bzImage 
}

