DESCRIPTION = "Rust based radio frontend"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo-update-recipe-crates cargo pkgconfig

DEPENDS = "libdbus-c++"

#pull in generated crate info
include ${BPN}-crates.inc

S = "${WORKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	git://github.com/uglyoldbob/radio.git;protocol=https;branch=master \
"

SRCREV = "5ba3bd7645adedf16a57c2f9ed1f3f44f1c16683"
