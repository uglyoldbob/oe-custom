SUMMARY = "LiteX - Picorv32 CPU"
HOMEPAGE = "https://github.com/litex-hub/pythondata-cpu-picorv32"
SECTION = "devel/hdl"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/litex-hub/pythondata-cpu-picorv32.git;protocol=https;branch=master"
SRCREV = "a6e8fd113527b39c1d18837968de7e6b9b59ea92"
PV = "2024.10+git${SRCPV}"

S = "${WORKDIR}/git"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
