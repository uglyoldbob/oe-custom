require recipes-kernel/linux/linux-yocto_6.12.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "xjrad"

SRC_URI:append = " \
    file://xjrad;type=kmeta;destsuffix=xjrad \
    file://0001-Add-the-device-tree-to-the-kernel.patch \
"
