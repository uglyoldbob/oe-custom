require recipes-kernel/linux/linux-yocto-tiny_6.12.bb
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE = "altera-deca"

SRC_URI:append = " \
    file://altera-deca;type=kmeta;destsuffix=altera-deca \
"
