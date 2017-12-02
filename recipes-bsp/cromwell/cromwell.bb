DESCRIPTION = "Cromwell"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PROVIDES = "virtual/bootloader"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

S = "${WORKDIR}/git"
COMPATIBLE_MACHINE = "xbox"

PR = "r0"

SRCREV = "master"
SRC_URI = "git://github.com/not404/cromwell.git \
	file://0001-Fixups-to-allow-compiling-with-recent-gcc.patch \
"

do_configure () {
}

do_compile () {
	export CC="${TARGET_PREFIX}gcc"
	export LD="${TARGET_PREFIX}ld"
	export OBJCOPY="${TARGET_PREFIX}objcopy"
	make
}

