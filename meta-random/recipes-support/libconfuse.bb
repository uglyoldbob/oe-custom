SUMMARY = "Library for parsing configuration files"
DESCRIPTION = "libConfuse is a configuration file parser library written in C. It \
 supports sections and (lists of) values (strings, integers, floats, \
 booleans or other sections), as well as some other features (such as \
 single/double-quoted strings, environment variable expansion, \
 functions and nested include statements)."
HOMEPAGE = "http://www.nongnu.org/confuse/"

LICENSE = "ISC"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://LICENSE;md5=42fa47330d4051cd219f7d99d023de3a"

inherit autotools binconfig pkgconfig lib_package gettext

EXTRA_OECONF = "--enable-shared --enable-static --disable-silent-rules"

BBCLASSEXTEND = "native"

SRCREV = "67fac45d54856e3817def4ef49807cf921132158"
SRC_URI = "git://github.com/martinh/libconfuse.git;branch=master"

do_configure:prepend() {
  touch ${S}/support/config.rpath
}

