LICENSE="MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

DEPENDS = "u-boot-tools-native"

SRC_URI:xjrad = "file://boot.cmd"

do_compile() {
   ${WORKDIR}/recipe-sysroot-native/usr/bin/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/boot.scr
}

do_install() {
	cp ${WORKDIR}/boot.scr ${D}/boot.scr
}

FILES:${PN} = "/boot.scr"
