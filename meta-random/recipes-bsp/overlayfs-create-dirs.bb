SUMMARY = "Overlayfs script"
DESCRIPTION = "Depend on this recipe to get the overlayfs-create-dirs.sh script"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://overlayfs-create-dirs.sh"

do_install:append() {
	install -d ${D}/usr/sbin
	install -m 0700 ${UNPACKDIR}/overlayfs-create-dirs.sh ${D}/usr/sbin/overlayfs-create-dirs.sh
}

FILES:${PN} += "/usr/local/bin/install.sh"

