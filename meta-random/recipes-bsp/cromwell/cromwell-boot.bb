DESCRIPTION = "Cromwell boot configuration file"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE="CLOSED"

inherit deploy

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = "file://linuxboot.cfg"

S = "${WORKDIR}"
COMPATIBLE_MACHINE = "xbox"

PR = "r2"

do_configure () {
}

do_compile () {
}

do_install() {
       install -m 644 ${S}/linuxboot.cfg ${D}/linuxboot.cfg
}

do_deploy() {
       install -m 644 ${S}/linuxboot.cfg ${DEPLOYDIR}/linuxboot.cfg
}

addtask deploy before do_build after do_compile

FILES:${PN} = "/linuxboot.cfg"
