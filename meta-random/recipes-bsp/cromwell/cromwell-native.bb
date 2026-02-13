DESCRIPTION = "Cromwell"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PROVIDES = "virtual/bootloader"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

S = "${WORKDIR}/git"
COMPATIBLE_MACHINE = "xbox"

PR = "r0"

inherit native

SRCREV = "2e1067aa837ca22462e49c880f20b8fa6bd38b77"
SRC_URI = "git://github.com/not404/cromwell.git \
	file://0001-Fixups-to-allow-compiling-with-recent-gcc.patch \
	file://0002-Modifications-for-newer-toolchain.patch \
	file://0003-Revert-some-changes-implemented-to-make-it-compile.patch \
"

