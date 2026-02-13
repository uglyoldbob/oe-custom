SUMMARY = "CppUTest is a C/C++ based unit xUnit test framework for unit testing."
HOMEPAGE = "http://cpputest.github.io/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=ce5d5f1fe02bcd1343ced64a06fd4177"

SRCREV = "e25097614e1c4856036366877a02346c4b36bb5b"

inherit cmake
inherit autotools
inherit native

SRC_URI = "git://github.com/cpputest/cpputest.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

do_install:append() {
	install -d ${D}${libdir}
	install -m 0644 lib/libCppUTest.a ${D}${libdir}
	install -m 0644 lib/libCppUTestExt.a ${D}${libdir}
}
