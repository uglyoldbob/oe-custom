SUMMARY = "Applies overlayfs for bluetooth data"
DESCRIPTION = "Applies configuration to allow bluetooth data to be persistent"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

S = "${WORKDIR}/src"

RDEPENDS:${PN} = "overlayfs-create-dirs"

inherit overlayfs

DISTRO_FEATURES:append = " overlayfs"
OVERLAYFS_WRITABLE_PATHS[bluetooth] += "/var/lib/bluetooth"
