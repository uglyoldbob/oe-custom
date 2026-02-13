DESCRIPTION = "On screen keyboard"
SECTION = "base"
DEPENDS = " \
 python3-distutils-extra \
 dconf \
 gsettings-desktop-schemas \
 hunspell \
 libxkbfile \
 libcanberra \
 python3-pygobject \
 python3-pycairo"
RDEPENDS:${PN} = " \
 ncurses \
 gsettings-desktop-schemas \
 iso-codes \
 python3-image \
 python3-core \
 python3-subprocess \
 python3-enum \
 python3-threading \
 python3-logging \
 python3-stringold \
 python3-pygobject \
 python3-pkgutil \
 python3-importlib \
 python3-textutils \
 python3-shell \
 python3-compression \
 python3-pycairo \
 python3-xml "
LICENSE = "GPL-3+"

LIC_FILES_CHKSUM = "file://COPYING;md5=df525ece2aec4bd4e3854d0a7cd08aa9"
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"

inherit setuptools3

FILES:${PN} = "/usr /etc"

DISTUTILS_INSTALL_ARGS = "--prefix=${D}/${prefix} \
    --install-data=${D}/${datadir}"

do_install:append () {
  install -d ${D}${sysconfdir}/xdg/autostart
  cp -frv ${S}/build/share/applications/onboard.desktop ${D}${sysconfdir}/xdg/autostart/
  install -d ${D}/usr/share/glib-2.0/schemas/
  cp ${S}/data/org.onboard.gschema.xml ${D}/usr/share/glib-2.0/schemas/
  glib-compile-schemas ${D}/usr/share/glib-2.0/schemas
}

SRCREV = "2253"
SRC_URI = "bzr://launchpad.net/onboard/1.4 \
 file://0001-Don-t-copy-xdg-file-using-python.patch \
"


S = "${WORKDIR}/1.4"


