SUMMARY = "LiteX - SdCard controller"
HOMEPAGE = "https://opencores.org/projects/sd_card_controller"
SECTION = "devel/hdl"

LICENSE = "LGPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/LGPL-2.0-or-later;md5=6d2d9952d88b50a51a5c73dc431d06c7"

SRC_URI = "svn://opencores.org/ocsvn/sd_card_controller/sd_card_controller;module=trunk;protocol=https;subdir=opencores_sdcard;subpath=trunk"
SRCREV = "15"
PV = "15"

S = "${UNPACKDIR}"

inherit setuptools3

BBCLASSEXTEND = "native nativesdk"
