DESCRIPTION = "Rust iot manager"
SECTION = "base"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://../LICENSE;md5=d41d8cd98f00b204e9800998ecf8427e"

inherit cargo-update-recipe-crates cargo pkgconfig

#pull in generated crate info
require ${BPN}-crates.inc

DEPENDS = "openssl bindgen-cli"

S = "${WORKDIR}/git"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://LICENSE \
	git://github.com/uglyoldbob/iot.git;protocol=https;branch=master \
	git://github.com/uglyoldbob/html.git;protocol=https;name=html;branch=main;workspace=crates/html;destsuffix=cargo_home/git/html-0.6.3 \
	git://github.com/uglyoldbob/ocsp-rs.git;protocol=https;name=ocsp;branch=master;destsuffix=cargo_home/git/ocsp-rs \
	git://github.com/uglyoldbob/rcgen.git;protocol=https;name=rcgen;workspace=rcgen;branch=attributes;destsuffix=cargo_home/git/rcgen \
	git://github.com/uglyoldbob/rust-runas.git;protocol=https;name=runas;branch=master;destsuffix=cargo_home/git/rust-runas \
	git://github.com/uglyoldbob/service.git;protocol=https;name=service;workspace=service;branch=master;destsuffix=cargo_home/git/service \
	git://github.com/uglyoldbob/rust-tss-esapi.git;protocol=https;name=tss-esapi;workspace=tss-esapi;branch=main3;destsuffix=cargo_home/git/rust-tss-esapi \
	crate://crates.io/msbuild/0.1.0 \
"

SRCREV_FORMAT = "default_html_ocsp_rcgen_runas_service_rust-tss-esapi"
SRCREV = "867eace05d299889fdaa6bc86c8c29114b5eed25"
SRCREV_html = "ac25dd9e55b9d9729cdb157690c7c38d867021c8"
SRCREV_ocsp = "cbb2858ce3b5875307bf38cabbad54a0bf20c215"
SRCREV_rcgen = "776f76f9f9f4c2ecec3a29aee9d84b077f2290d8"
SRCREV_runas = "29bdb2501c05b7d78e63628bedbd4a190b71bad3"
SRCREV_service = "fa8c37afdfa67bc82587ad8723012c91207f43a8"
SRCREV_tss-esapi = "223758866b788aac7f545dc490cb251458c07472"
SRC_URI[msbuild-0.1.0.sha256sum] = "75f10d2a26dd8489c4b0dce152371d6f1aabbc9cf52855089bca00a3ce5fdca3"
