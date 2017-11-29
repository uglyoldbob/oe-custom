DESCRIPTION = "U-Boot - the Universal Boot Loader"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"

S = "${WORKDIR}/git"

DEPENDS = "libgcc"

PR = "r7"

SRCREV = "master"
SRC_URI = "git://github.com/ashcharles/verdex-uboot.git \
	"

do_configure () {
	make gumstix_config
}

do_compile () {
	export CROSS_COMPILE=" "
	export HOSTCC="${BUILD_CC}"
	make tools
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 tools/mkimage ${D}${bindir}/uboot-verdex-mkimage
}

inherit native
