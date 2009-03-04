# This file is included by the top-level libcore Android.mk.
# It's not a normal makefile, so we don't include CLEAR_VARS
# or BUILD_*_LIBRARY.

LOCAL_SRC_FILES := org_apache_harmony_xml_ExpatParser.cpp 

LOCAL_C_INCLUDES += \
	external/expat/lib

# Any shared/static libs that are listed here must also
# be listed in libs/nativehelper/Android.mk.
# TODO: fix this requirement

LOCAL_SHARED_LIBRARIES += \
	libcutils \
	libexpat \
	libutils

#LOCAL_STATIC_LIBRARIES +=

