#!/bin/sh

echo "Starting xbox qemu using prebuild bios"
./i386-softmmu/qemu-system-i386 -cpu pentium3 -machine xbox,bootrom=./mcpx_1.0.bin -m 64 -usb -device usb-xbox-gamepad -bios ./cromwell_1024.bin -s -S &

gdb --command=./xboxoldgdb
