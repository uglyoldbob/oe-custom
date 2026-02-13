LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
SUMMARY = "Basic grub.cfg for use in non-EFI systems"
DESCRIPTION = "Grub might require different configuration file for \
different machines."
HOMEPAGE = "https://www.gnu.org/software/grub/manual/grub/grub.html#Configuration"

RPROVIDES:${PN} += "bootconf"

SRC_URI = "file://grub.cfg"

S = "${WORKDIR}/sources"
UNPACKDIR = "${S}"

do_install() {
	install -d ${D}/usr/share
	install -m 0500 ${UNPACKDIR}/grub.cfg ${D}/usr/share/grub.cfg
}

FILES:${PN} = "/usr/share/grub.cfg"
