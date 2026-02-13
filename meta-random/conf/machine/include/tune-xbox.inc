DEFAULTTUNE ?= "xbox"

TUNE_ARCH = "i386"
TUNE_PKGARCH = "xbox"

# Extra tune features
TUNEVALID[xbox] = "Enable xbox specific processor optimizations"
TUNE_CCARGS .= "${@bb.utils.contains('TUNE_FEATURES', 'xbox', ' -march=pentium', '', d)}"
TUNE_CCARGS .= " -Wa,--divide"

# Extra tune selections
AVAILTUNES += "xbox"
TUNE_FEATURES_tune-xbox = "xbox"
BASE_LIB_tune-xbox = "lib"
TUNE_PKGARCH_tune-xbox = "xbox"
#PACKAGE_EXTRA_ARCHS_tune-xbox = "${PACKAGE_EXTRA_ARCHS_tune-x86} xbox"
