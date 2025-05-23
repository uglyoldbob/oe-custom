FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:${THISDIR}/files:"

COMPATIBLE_MACHINE:oldpc-64 = "oldpc-64"
COMPATIBLE_MACHINE:pc-64 = "pc-64"
COMPATIBLE_MACHINE:epia5000 = "epia5000"

SRC_URI:append:epia5000 = " \
    file://epia5000;type=kmeta;destsuffix=epia5000 \
"

SRC_URI += "file://fragment.cfg \
	    file://logo_linux_clut224.ppm \ 
"

SRCREV_epia5000 = "64291f7db5bd8150a74ad2036f1037e6a0428df2"

do_configure:prepend() {
    install -m 0644 ${UNPACKDIR}/logo_linux_clut224.ppm ${S}/drivers/video/logo/logo_linux_clut224.ppm
}
