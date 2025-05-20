#!/bin/bash

echo "Autoinstall is starting" > /dev/kmsg

PARTITION=`cat /etc/initialize-mmc`
DISK=${PARTITION%p?}

echo "The disk is $DISK"

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount 1"
  while umount ${DISK}1; do :; done
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  while umount ${DISK}4; do :; done
  sleep 1
done
mount

sfdisk $DISK < /etc/autoinstall/partitions

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount 2"
  while umount ${DISK}1; do :; done
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  while umount ${DISK}4; do :; done
  sleep 1
done
mount

mkfs.fat ${DISK}1

mkfs.ext4 -F ${DISK}2
mkfs.ext4 -F ${DISK}3

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount 3"
  while umount ${DISK}1; do :; done
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  while umount ${DISK}4; do :; done
  sleep 1
done
mount

mkfs.ext4 -F ${DISK}4

mkdir /var/install
mount ${DISK}4 /var/install
mkdir -p /var/install/overlay-etc/lower
mkdir -p /var/install/overlay-etc/upper
mkdir -p /var/install/overlay-etc/work

if [ ! -f /etc/initial.swu ]; then
    echo "Initial swu file missing from sd card"
    exit 1
fi

ln -sf ${DISK}2 /dev/update
swupdate -v -e stable,copy1 -f /etc/swupdate.cfg -k /etc/swupdatepub.key -K /etc/swupdate/encryption -i /etc/initial.swu -p ""

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount 4"
  while umount ${DISK}1; do :; done
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  while umount ${DISK}4; do :; done
  sleep 1
done
mount

mkdir /var/boot
mount ${DISK}1 /var/boot
mkdir /var/boot/grub
cp /boot/grub/grubenv /var/boot/grub/grubenv

mkdir /tmp/t
mount ${DISK}2 /tmp/t
mkdir /tmp/s
mount ${DISK}1 /tmp/s
grub-install --boot-directory=/tmp/s --removable --target x86_64-efi --efi-directory=/tmp/s ${DISK}
mkdir -p /tmp/s/grub
cp /usr/share/grub.cfg /tmp/s/grub/grub.cfg
grub-editenv /tmp/s/grub/grubenv set bootpart=2
umount /tmp/s
umount /tmp/t

rm -rf /tmp/s
rm -rf /tmp/t

hwclock -f /dev/rtc1 --set --date "2025-03-01"

sync
echo "Autoinstall is complete" > /dev/kmsg

