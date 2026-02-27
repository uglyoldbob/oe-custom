DESCRIPTION = "Rust based radio frontend"
SECTION = "base"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit cargo-update-recipe-crates cargo pkgconfig system-version

DEPENDS = "\
 libdbus-c++ \
 bindgen-cli \
 clang \
 clang-native \
 alsa-lib \
 protobuf \
 protobuf-native \
 swupdate \
"

#pull in generated crate info
include ${BPN}-crates.inc

UNPACKDIR ??= "${WORKDIR}"

S = "${UNPACKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	gitsm://github.com/uglyoldbob/radio.git;protocol=https;branch=master;name=default \
	git://github.com/guspower/egui_virtual_keyboard.git;protocol=https;branch=egui-0.31.0;name=egui_virtual_keyboard;destsuffix=egui_virtual_keyboard \
	file://radio.service \
	file://radio-gui.service \
"
SRCREV_default = "1c1a2bd81d6721ed92f30a72f950418f910020e6"
SRCREV_egui_virtual_keyboard = "c2f3cc26d1028cd23f0630a56c3e8c9173ed9ab8"
SRCREV_FORMAT = "default_egui_virtual_keyboard"

SRC_URI[nmrs-2.0.0.sha256sum] = "7fde6b64792a00832173305aa08846fbea4e403bbb8caee9f9b48dc1aa40fe95"
SRC_URI[rgb-0.8.52.sha256sum] = "0c6a884d2998352bb4daf0183589aec883f16a6da1f4dde84d8e2e9a5409a1ce"
SRC_URI[android-auto-0.1.0.sha256sum] = "7ddcd902db329bc2f91abb9dd62aa7219c7949f3edc4ecc26424bf111de58394"
SRC_URI[bluetooth-rust-0.1.0.sha256sum] = "d902a85a6a6350272f3211f2aaae918b47fb99ce947e5ef5e3cb05a9e8279619"

CARGO_BUILD_FLAGS += " -F swupdate,wifi,bluetooth,androidauto"

do_compile:prepend() {
	case ${DISTRO_CODENAME} in
		"walnascar")
			export BINDGEN_EXTRA_CLANG_ARGS=-I${RECIPE_SYSROOT}/usr/lib/clang/20/include
			;;
		"scarthgap")
			export BINDGEN_EXTRA_CLANG_ARGS=-I${RECIPE_SYSROOT}/usr/include
			;;
		*)
			bberror "Unexpected distro: ${DISTRO_CODENAME}"
			;;
	esac
	export AUTO_FULLSCREEN="yes"
	export UPDATE_SERVER="${UPDATE_SERVER}"
	export SOFTWARE_VERSION="${SYSTEM_VERSION}"
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
