FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

COMPATIBLE_MACHINE:epia5000 = "epia5000"

SRC_URI:append:epia5000 = " \
    file://epia5000;type=kmeta;destsuffix=epia5000 \
"

SRCREV_epia5000="64291f7db5bd8150a74ad2036f1037e6a0428df2"
