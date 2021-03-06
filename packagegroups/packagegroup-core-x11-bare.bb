SUMMARY = "Basic X11 session"
DESCRIPTION = "Packages required to set up a basic working X11 session"
LICENSE = "MIT"
PR = "r1"

inherit packagegroup distro_features_check
# rdepends on matchbox-wm
REQUIRED_DISTRO_FEATURES = "x11"

RDEPENDS_${PN} = "\
    packagegroup-core-x11-xserver \
    packagegroup-core-x11-utils \
    dbus \
    pointercal \
    mini-x-session \
    liberation-fonts \
    "
