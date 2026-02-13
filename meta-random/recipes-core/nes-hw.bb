SUMMARY="Console emulators"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

S = "${WORKDIR}/git"

SRC_URI = "gitsm://github.com/uglyoldbob/old_systems.git;protocol=https;branch=master"
SRCREV = "master"

inherit deploy

INHIBIT_DEFAULT_DEPS = "1"
DEPENDS += "gowin-native"

SECURITY_CFLAGS = "${SECURITY_NOPIE_CFLAGS}"
SECURITY_LDFLAGS = ""

do_compile() {
    cd ${S}/nes/hdl
    gw_sh ${S}/nes/hdl/run.tcl
}

do_install[noexec] = "1"

do_deploy () {
    install -Dm 0644 ${S}/nes/hdl/impl/pnr/project.fs ${DEPLOYDIR}/nes-hw.fs
}

addtask deploy before do_build after do_install
