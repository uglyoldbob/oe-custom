SUMMARY = "Install user ssh key as root"
DESCRIPTION = "Install user ssh key as root"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"

RDEPENDS:${PN} = "bash overlayfs-create-dirs"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

do_install() {
	install -d ${D}/home/root/.ssh
	if [[ -r ${TOPDIR}/conf/ssh.pub ]]; then
	    echo "Installing ssh key"
	    cat ${TOPDIR}/conf/ssh.pub >> ${D}/home/root/.ssh/authorized_keys
	    chmod 0600 ${D}/home/root/.ssh/authorized_keys
	fi
}

FILES:${PN} += "/home/root/.ssh /home/root/.ssh/authorized_keys"

inherit overlayfs

DISTRO_FEATURES:append = " overlayfs"
OVERLAYFS_WRITABLE_PATHS[homessh] += "/home/root/.ssh"
