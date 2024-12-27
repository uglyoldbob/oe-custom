SUMMARY = "Custom LiteX boards files"
HOMEPAGE = "https://github.com/uglyoldbob/custom-litex-boards"
SECTION = "devel/hdl"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-2-Clause;md5=cb641bc04cda31daea161b1bc15da69f"

SRC_URI = "git://github.com/uglyoldbob/custom-litex-boards;protocol=https;branch=master"

S = "${WORKDIR}/git"

DEFAULT_PREFERENCE = "-1"

SRCREV = "${AUTOREV}"

inherit setuptools3

RDEPENDS:${PN} += "litex"
RDEPENDS:${PN} += "litedram"
RDEPENDS:${PN} += "liteeth"
RDEPENDS:${PN} += "litesata"
RDEPENDS:${PN} += "litepcie"
RDEPENDS:${PN} += "litehyperbus"

BBCLASSEXTEND = "native nativesdk"
