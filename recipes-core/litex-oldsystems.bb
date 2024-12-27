SUMMARY = "LiteX - Old console emulators"
HOMEPAGE = "https://github.com/uglyoldbob/old_systems"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/uglyoldbob/old_systems.git;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
PV = "1.65"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
