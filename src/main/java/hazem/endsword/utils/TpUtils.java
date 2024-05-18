package hazem.endsword.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class TpUtils {

    public static Block AirSafeTP(Player p, Block block) {
        if (IsBlockValid(block)) {
            if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, -1, 0)))) {
                return p.getWorld().getBlockAt(block.getLocation().add(0, -1, 0));
            } else if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 1, 0)))) {
                return block;
            }
        }
        return null;
    }

    public static boolean IsBlockValid(Block block) {
        return block.isEmpty() || block.isLiquid();
    }

    public static Block SafeTpTo(Player p, Block block, BlockFace face) {
        return switch (face) {
            case UP -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 1, 0))) && IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 2, 0)))) {
                    yield p.getWorld().getBlockAt(block.getLocation().add(0, 1, 0));
                }
                yield null;
            }
            case DOWN -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, -1, 0))) && IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, -2, 0)))) {
                    yield p.getWorld().getBlockAt(block.getLocation().add(0, -2, 0));
                }
                yield null;
            }
            case NORTH -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 0, -1)))) {
                    if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, -1, -1)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(0, -1, -1));
                    } else if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 1, -1)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(0, 0, -1));
                    }
                }
                yield null;
            }
            case SOUTH -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 0, 1)))) {
                    if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, -1, 1)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(0, -1, 1));
                    } else if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(0, 1, 1)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(0, 0, 1));
                    }
                }
                yield null;
            }
            case EAST -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(1, 0, 0)))) {
                    if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(1, -1, 0)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(1, -1, 0));
                    } else if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(1, 1, 0)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(1, 0, 0));
                    }
                }
                yield null;
            }
            case WEST -> {
                if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(-1, 0, 0)))) {
                    if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(-1, -1, 0)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(-1, -1, 0));
                    } else if (IsBlockValid(p.getWorld().getBlockAt(block.getLocation().add(-1, 1, 0)))) {
                        yield p.getWorld().getBlockAt(block.getLocation().add(-1, 0, 0));
                    }
                }
                yield null;
            }
            default -> null;
        };
    }

    public static double distanceBetweenLocations(Location loc1, Location loc2) {
        double dx = loc2.getX() - loc1.getX();
        double dy = loc2.getY() - loc1.getY();
        double dz = loc2.getZ() - loc1.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static Location getBlockFaceCentre(Location blockLoc, BlockFace face) {
        Location newLocation = blockLoc.clone();
        switch (face) {
            case UP -> {
                newLocation.setX(blockLoc.getBlockX() + 0.5);
                newLocation.setY(blockLoc.getBlockY() + 1);
                newLocation.setZ(blockLoc.getBlockZ() + 0.5);
            }
            case DOWN -> {
                newLocation.setX(blockLoc.getBlockX() + 0.5);
                newLocation.setZ(blockLoc.getBlockZ() + 0.5);
            }
            case NORTH -> {
                newLocation.setX(blockLoc.getBlockX() + 0.5);
                newLocation.setY(blockLoc.getBlockY() + 0.5);
                newLocation.setZ(blockLoc.getBlockZ());
            }
            case SOUTH -> {
                newLocation.setX(blockLoc.getBlockX() + 0.5);
                newLocation.setY(blockLoc.getBlockY() + 0.5);
                newLocation.setZ(blockLoc.getBlockZ() + 1);
            }
            case EAST -> {
                newLocation.setX(blockLoc.getBlockX() + 1);
                newLocation.setY(blockLoc.getBlockY() + 0.5);
                newLocation.setZ(blockLoc.getBlockZ() + 0.5);
            }
            case WEST -> {
                newLocation.setY(blockLoc.getBlockY() + 0.5);
                newLocation.setZ(blockLoc.getBlockZ() + 0.5);
            }
        }
        return newLocation;
    }
}
