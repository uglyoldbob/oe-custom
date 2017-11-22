DESCRIPTION = "U-Boot - the Universal Boot Loader"
HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=4c6cde5df68eff615d36789dc18edd3b"
PROVIDES = "virtual/bootloader"

S = "${WORKDIR}/git"

DEPENDS = "libgcc"

PR = "r7"

SRCREV = "master"
SRC_URI = "git://github.com/ashcharles/verdex-uboot.git \
	"

do_configure () {
	export CROSS_COMPILE="${TARGET_PREFIX}"
	make gumstix_config
}

do_compile () {
	export CROSS_COMPILE=" "
	make tools	
	export CROSS_COMPILE="${TARGET_PREFIX}"
	export HOSTCC="${BUILD_CC}"
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS
	make CC="$CC" u-boot.bin
}
