SUMMARY = "Lattice Radiant"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native bzip2-native"

SRC_URI = "file://lattice-radiant-license.dat \
        file://2024.2.0.3.4_Radiant_lin.zip \
"

SRC_URI[sha256sum] = "1cd0b9ce86897509b12f05bebd0ec2a7b193b7168c37d82676584e9211a6e2fa"

S = "${UNPACKDIR}"

inherit terminal native

do_configure[network] = "1"

do_configure() {
        ${UNPACKDIR}/2024.2.0.3.4_Radiant_lin.run --help
        mkdir -p ${WORKDIR}/radiant
        cd ${UNPACKDIR}
        ./2024.2.0.3.4_Radiant_lin.run --console --prefix ${WORKDIR}/radiant
}

do_compile() {
}

SYSROOT_DIRS_NATIVE += "${prefix}/lattice-radiant"
INHIBIT_SYSROOT_STRIP = "1"

do_install() {
	install -d ${D}${prefix}/lattice-radiant
	cp -r ${WORKDIR}/radiant ${D}${prefix}/lattice-radiant
	cp ${UNPACKDIR}/lattice-radiant-license.dat ${D}${prefix}/lattice-radiant/radiant/license/license.dat
	install -d ${D}${bindir}
	ln -s -r ${D}${prefix}/lattice-radiant/radiant/bin/lin64/radiantc ${D}${bindir}/radiantc
}
