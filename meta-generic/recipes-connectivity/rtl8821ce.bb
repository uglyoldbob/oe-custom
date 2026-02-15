SUMMARY = "RTL8821ce kernel driver"
DESCRIPTION = "RTL8821ce pcie wifi kernel driver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "git://github.com/tomaspinho/rtl8821ce.git;protocol=https;branch=master"
SRCREV = "6208d1dd190b7cb60bbbf760505bcb1c250518c2"
S = "${WORKDIR}/git"

DEPENDS = "bc-native"

inherit module

EXTRA_OEMAKE += "KSRC=${STAGING_KERNEL_DIR} MODDESTDIR=${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless"
MODULES_INSTALL_TARGET = "install"

do_install:prepend() {
    mkdir -p ${D}/${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless
}

#FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/8821ce.ko"

RPROVIDES:${PN} += "kernel-module-8821ce"
