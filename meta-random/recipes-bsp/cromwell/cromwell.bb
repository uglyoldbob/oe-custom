DESCRIPTION = "Cromwell"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
PROVIDES = "virtual/bootloader"

inherit deploy

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

S = "${WORKDIR}/git"
COMPATIBLE_MACHINE = "xbox"

PR = "r0"

do_configure () {
}

do_compile () {
       export CC="${TARGET_PREFIX}gcc"
       export LD="${TARGET_PREFIX}ld"
       export OBJCOPY="${TARGET_PREFIX}objcopy"
       make
}

SRCREV = "716422416e51c00634508dc9ea6d365a6b1b9a9f"
SRC_URI = "git://github.com/XboxDev/cromwell.git;protocol=https;branch=master"

do_deploy() {
       install -m 644 ${S}/image/cromwell.bin ${DEPLOYDIR}/cromwell.bin
       install -m 644 ${S}/obj/image-crom.elf ${DEPLOYDIR}/cromwell.elf
}

addtask deploy before do_build after do_compile
