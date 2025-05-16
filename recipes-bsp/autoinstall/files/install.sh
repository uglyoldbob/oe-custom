#!/bin/bash

echo "Autoinstall is starting" > /dev/kmsg

PARTITION=`cat /etc/initialize-mmc`
DISK=${PARTITION%p?}

echo "The disk is $DISK"

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount"
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  sleep 1
done
mount

sfdisk $DISK < /etc/autoinstall/partitions

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount"
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  sleep 1
done
mount

mkfs.ext4 -F ${DISK}2

while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount"
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  sleep 1
done
mount

mkfs.ext4 -F ${DISK}3

mkdir /var/install
mount ${DISK}3 /var/install
mkdir -p /var/install/overlay-etc/lower
mkdir -p /var/install/overlay-etc/upper
mkdir -p /var/install/overlay-etc/work

if [ ! -f /etc/initial.swu ]; then
    echo "Initial swu file missing from sd card"
    exit 1
fi

SWU_MACHINE=$(< /etc/swumachine)

echo "Machine to install for is $SWU_MACHINE"

swupdate -H $SWU_MACHINE -v -f /etc/swupdate.cfg -k /etc/swupdatepub.key -K /etc/swupdate/encryption -i /etc/initial.swu

mkdir /var/sdcard
mount ${DISK}1 /var/sdcard


while [[ `mount | grep ${DISK} | wc -l` -gt 0 ]]; do
  echo "Trying to unmount"
  while umount ${DISK}1; do :; done
  while umount ${DISK}2; do :; done
  while umount ${DISK}3; do :; done
  sleep 1
done
mount

hwclock -f /dev/rtc1 --set --date "2025-03-01"

echo "Autoinstall is complete" > /dev/kmsg

