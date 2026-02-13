DESCRIPTION = "Rust based radio frontend"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo-update-recipe-crates cargo pkgconfig

DEPENDS = "libdbus-c++ bindgen-cli clang alsa-lib protobuf protobuf-native"

#pull in generated crate info
include ${BPN}-crates.inc

UNPACKDIR ??= "${WORKDIR}"

S = "${UNPACKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	gitsm://github.com/uglyoldbob/radio.git;protocol=https;branch=master \
	file://radio.service \
	file://radio-gui.service \
"
SRCREV = "${AUTOREV}"

SRC_URI[android-auto-0.1.0.sha256sum] = "7ddcd902db329bc2f91abb9dd62aa7219c7949f3edc4ecc26424bf111de58394"
SRC_URI[bluetooth-rust-0.1.0.sha256sum] = "d902a85a6a6350272f3211f2aaae918b47fb99ce947e5ef5e3cb05a9e8279619"

do_compile:prepend() {
	case ${DISTRO_CODENAME} in
		"walnascar")
			export BINDGEN_EXTRA_CLANG_ARGS=-I${RECIPE_SYSROOT}/usr/lib/clang/20/include
			;;
		"scarthgap")
			export BINDGEN_EXTRA_CLANG_ARGS=-I${RECIPE_SYSROOT}/usr/lib/clang/18/include
			;;
		*)
			bberror "Unexpected distro: ${DISTRO_CODENAME}"
			;;
	esac
}

do_install:append() {
	install -d ${D}/etc/systemd/system/multi-user.target.wants
	install -d ${D}/etc/systemd/system
	install -m 0600 ${UNPACKDIR}/radio.service ${D}/etc/systemd/system/radio.service
	ln -s /etc/systemd/system/radio.service ${D}/etc/systemd/system/multi-user.target.wants/radio.service

	install -d ${D}/etc/systemd/system/graphical.target.wants
	install -m 0600 ${UNPACKDIR}/radio-gui.service ${D}/etc/systemd/system/radio-gui.service
	ln -s /etc/systemd/system/radio-gui.service ${D}/etc/systemd/system/graphical.target.wants/radio-gui.service
}
