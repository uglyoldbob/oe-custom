require recipes-kernel/linux/linux-yocto-tiny_6.12.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "tang-nano-20k"

SRC_URI:append = " \
    file://tang-nano-20k;type=kmeta;destsuffix=tang-nano-20k \
"
