# Tiny Takeover Backport

<a href='https://fabricmc.net'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://neoforged.net/'><img alt="neoforge" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/neoforge_vector.svg"></a>

**Tiny Takeover Backport** brings features from the official **Tiny Takeover** (26.1) game drop back to older
Minecraft versions!

## Features

All backported features use the `minecraft:` namespace (e.g., `minecraft:golden_dandelion`) to ensure seamless world
migration when updating to Minecraft 26.1+.

### Redesigned Baby Mobs

- Backports updated models, textures, and properties for baby variants.

### Adult Rabbit Model

- Backports the updated adult rabbit model along with the new hitbox.

### Golden Dandelion:

- Crafted by surrounding a dandelion with gold nuggets.
- Right-click any baby mob with a Golden Dandelion to **lock their age/growth**, keeping them tiny forever!
  Right-clicking again will unlock their growth.
- Can be potted in flower pots, crafted into Suspicious Stew, or used for Piglin trading.

### Craftable Name Tags

- Crafted using any nugget and paper.

## Mod Compatibility

- **Vanilla Backport**: When loaded alongside [Vanilla Backport](https://modrinth.com/mod/vanillabackport/), the mod
  features additional support for their backported warm/cold mob variants.
- **Modded mob variants**: Baby textures are included for variants added by Environmental, Autumnity, No Man's Land,
  Nature's Spirit, Neapolitan, Quark and Nirvana. Variants without a baby texture fall back to the old baby model.

## Known Issues

- **Quark** > Disable "Variant Animal Textures" in the config, otherwise those babies use the old baby model
- **Vanilla Backport** > Disable "Use Sheep Wool Undercoat" in client config
- **Environmental** > Disable "Baby Chicken Texture" in client config, otherwise baby chickens use the old baby model
- **Personality** > Disable "Sheep Fur Overlay" in client config

## Configuration

The mod is fully configurable using an in-game config screen (requires
**[YetAnotherConfigLib](https://modrinth.com/mod/yacl)**). You can:

- Toggle baby models individually for each mob type.
    - This even supports hot reloading!
- Enable/disable the adult rabbit model replacement.
- Enable baby models for modded mobs that reuse a vanilla model (off by default).
- Blacklist specific mobs from using the new baby models.
- Toggle the craftable Name Tag recipe.

### ⚠️ Disclaimer

This mod is in no way affiliated with or endorsed by Mojang, Microsoft, or any of their subsidiaries.  
Minecraft is a trademark of Mojang Studios. All related assets, including textures, names, and game content, are the
property of Mojang Studios and Microsoft.

No commercial use is intended or permitted.

## License

[![Code license (MIT)](https://img.shields.io/badge/code%20license-MIT-green.svg?style=flat-square)](https://github.com/evanbones/Tiny-Takeover-Backport/blob/1.20.1/LICENSE)

---

[![discord-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/social/discord-plural_vector.svg)](https://discord.com/invite/JcGRdT6Pbx) [![github-plural](https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/social/github-plural_vector.svg)](https://github.com/evanbones/Tiny-Takeover-Backport)
