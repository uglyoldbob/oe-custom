SUMMARY = "LiteX - Rocket CPU"
HOMEPAGE = "https://github.com/litex-hub/pythondata-cpu-rocket"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/litex-hub/pythondata-cpu-rocket.git;protocol=https;branch=master"
SRCREV = "55d7e42913e46ba33e2ade792eef13191c895852"
PV = "2024.10+git${SRCPV}"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
