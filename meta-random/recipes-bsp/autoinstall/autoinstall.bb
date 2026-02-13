SUMMARY = "Autoinstall swu image on boot"
DESCRIPTION = "Autoinstalls an swu image"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"

RDEPENDS:${PN} = "bash e2fsprogs-mke2fs dosfstools util-linux"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += "file://install.sh \
	file://autoinstall.service \
	file://partitions_mmc \
"

do_install:append() {
	install -d ${D}/usr/local/bin
	install -m 0700 ${UNPACKDIR}/install.sh ${D}/usr/local/bin/install.sh
	install -d ${D}/etc/systemd/system
	install -m 0600 ${UNPACKDIR}/autoinstall.service ${D}/etc/systemd/system/autoinstall.service
	install -d ${D}/etc/systemd/system/multi-user.target.wants
	ln -s /etc/systemd/system/autoinstall.service ${D}/etc/systemd/system/multi-user.target.wants/autoinstall.service
	install -d ${D}/etc/autoinstall
	install -m 0600 ${UNPACKDIR}/partitions_mmc ${D}/etc/autoinstall/partitions
}

FILES:${PN} += "/usr/local/bin/install.sh"
