SUMMARY = "LiteX - BrianHg DDR3 controller"
HOMEPAGE = "https://github.com/BrianHGinc/BrianHG-DDR3-Controller"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/uglyoldbob/BrianHG-DDR3-Controller.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "1.65"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
