SUMMARY = "LiteX - Uber DDR3 controller"
HOMEPAGE = "https://github.com/AngeloJacobo/UberDDR3"
SECTION = "devel/hdl"

LICENSE = "GPL-3.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/uglyoldbob/UberDDR3.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "1.0"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
