SUMMARY = "Quartus lite"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

FILESEXTRAPATHS:prepend := "${TOPDIR}:${THISDIR}/${PN}:"

PACKAGES_DYNAMIC = "^${PN}-locale-.*"

DEPENDS = "glibc-locale-native"

SRC_URI = "\
	https://cdn.gowinsemi.com.cn/Gowin_V1.9.10.03_linux.tar.gz \
	file://gwlicense.ini \
"
SRC_URI[sha256sum] = "3eacbbe2e724a98a28e0b5653032a60a35e34379b093baffcfbb23e2e1a8ed65"

S = "${UNPACKDIR}"

inherit native

SYSROOT_DIRS_NATIVE += "${prefix}/gowin"

do_install() {
	install -d ${D}${prefix}/gowin
	install -d ${D}${bindir}
	cp -r ${UNPACKDIR}/* ${D}${prefix}/gowin
	rm ${D}${prefix}/gowin/IDE/lib/libfreetype.so.6
	ln -s -r ${D}${prefix}/gowin/IDE/bin/gw_sh ${D}${bindir}/gw_sh 
	cp ${UNPACKDIR}/gwlicense.ini ${D}${prefix}/gowin/IDE/bin/gwlicense.ini
}
