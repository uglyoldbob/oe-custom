#
# SPDX-License-Identifier: GPL-2.0-only
#
# DESCRIPTION
# This implements the 'isoimage-isohybrid' source plugin class for 'wic'
#
# AUTHORS
# Mihaly Varga <mihaly.varga (at] ni.com>

import glob
import logging
import os
import re
import shutil

from wic import WicError
from wic.engine import get_custom_config
from wic.pluginbase import SourcePlugin
from wic.misc import exec_cmd, exec_native_cmd, get_bitbake_var

logger = logging.getLogger('wic')

class PlainIsoImagePlugin(SourcePlugin):
    """
    Create a bootable ISO image

    This plugin creates a hybrid, legacy and EFI bootable ISO image. The
    generated image can be used on optical media as well as USB media.

    Legacy boot uses syslinux and EFI boot uses grub or gummiboot (not
    implemented yet) as bootloader. The plugin creates the directories required
    by bootloaders and populates them by creating and configuring the
    bootloader files.

    Example kickstart file:
    part /boot --source isoimage-isohybrid --sourceparams="loader=grub-efi, \\
    image_name= IsoImage" --ondisk cd --label LIVECD
    bootloader  --timeout=10  --append=" "

    In --sourceparams "loader" specifies the bootloader used for booting in EFI
    mode, while "image_name" specifies the name of the generated image. In the
    example above, wic creates an ISO image named IsoImage-cd.direct (default
    extension added by direct imeger plugin) and a file named IsoImage-cd.iso
    """

    name = 'iso'

    @classmethod
    def do_configure_partition(cls, part, source_params, creator, cr_workdir,
                               oe_builddir, bootimg_dir, kernel_dir,
                               native_sysroot):
        """
        Called before do_prepare_partition(), creates loader-specific config
        """
        isodir = "%s/ISO/" % cr_workdir

        if os.path.exists(isodir):
            shutil.rmtree(isodir)

        install_cmd = "install -d %s " % isodir
        exec_cmd(install_cmd)

        # Overwrite the name of the created image
        logger.debug(source_params)
        if 'image_name' in source_params and \
                    source_params['image_name'].strip():
            creator.name = source_params['image_name'].strip()
            logger.debug("The name of the image is: %s", creator.name)

    @classmethod
    def do_prepare_partition(cls, part, source_params, creator, cr_workdir,
                             oe_builddir, bootimg_dir, kernel_dir,
                             rootfs_dir, native_sysroot):
        """
        Called to do the actual content population for a partition i.e. it
        'prepares' the partition to be incorporated into the image.
        In this case, prepare content for a bootable ISO image.
        """

        isodir = "%s/ISO" % cr_workdir

        if part.rootfs_dir is None:
            if not 'ROOTFS_DIR' in rootfs_dir:
                raise WicError("Couldn't find --rootfs-dir, exiting.")
            rootfs_dir = rootfs_dir['ROOTFS_DIR']
        else:
            if part.rootfs_dir in rootfs_dir:
                rootfs_dir = rootfs_dir[part.rootfs_dir]
            elif part.rootfs_dir:
                rootfs_dir = part.rootfs_dir
            else:
                raise WicError("Couldn't find --rootfs-dir=%s connection "
                               "or it is not a valid path, exiting." %
                               part.rootfs_dir)

        if not os.path.isdir(rootfs_dir):
            rootfs_dir = get_bitbake_var("IMAGE_ROOTFS")
        if not os.path.isdir(rootfs_dir):
            raise WicError("Couldn't find IMAGE_ROOTFS, exiting.")

        part.rootfs_dir = rootfs_dir
        deploy_dir = get_bitbake_var("DEPLOY_DIR_IMAGE")
        img_iso_dir = get_bitbake_var("ISODIR")

        # Remove the temporary file created by part.prepare_rootfs()
        if os.path.isfile(part.source_file):
            os.remove(part.source_file)

        logger.warning("I am groot\n")

        isodir = rootfs_dir

        #create ISO image
        iso_img = "%s/tempiso_img.iso" % cr_workdir

        mkisofs_cmd = "mkisofs -V %s " % part.label
        mkisofs_cmd += "-o %s -U " % iso_img
        mkisofs_cmd += "-J -joliet-long -r -iso-level 2 %s" % isodir

        exec_native_cmd(mkisofs_cmd, native_sysroot)

        du_cmd = "du -Lbks %s" % iso_img
        out = exec_cmd(du_cmd)
        isoimg_size = int(out.split()[0])

        full_path_iso = "%s/%s.iso" % (creator.kernel_dir, creator.name)

        shutil.copy2(iso_img, full_path_iso)

        part.size = isoimg_size
        part.source_file = iso_img

    @staticmethod
    def _do_print(thing):
        for attr in dir(thing):
            logger.warning(".%s = %s" % (attr, getattr(thing, attr)))

    @classmethod
    def do_install_disk(cls, disk, disk_name, creator, workdir, oe_builddir,
                        bootimg_dir, kernel_dir, native_sysroot):
        """
        Called after all partitions have been prepared and assembled into a
        disk image.  In this case, we insert/modify the MBR using isohybrid
        utility for booting via BIOS from disk storage devices.
        """
        
        iso_img = "%s.p1" % disk.path
        full_path_iso = "%s/%s.iso" % (creator.kernel_dir, creator.name)

        shutil.copy2(iso_img, full_path_iso)



