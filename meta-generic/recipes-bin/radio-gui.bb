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
 libimxvpuapi2 \
 libimxdmabuffer \
"

#pull in generated crate info
include ${BPN}-crates.inc

UNPACKDIR ??= "${WORKDIR}"

S = "${UNPACKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	gitsm://github.com/uglyoldbob/radio.git;protocol=https;branch=master;name=default \
	file://radio.service \
	file://radio-gui.service \
"
SRCREV_default = "efc97b14c45adcf18a5a96c05ab8227afa8489d5"
SRCREV_FORMAT = "default"

CARGO_BUILD_FLAGS += " --no-default-features -F swupdate,bluetooth,wifi,usb,androidauto,imxvpuapi2"

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
