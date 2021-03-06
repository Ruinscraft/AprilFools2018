package com.ruinscraft.aprilfools2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class AprilFools2018 extends JavaPlugin implements Listener {

	public static final Map<String, Property> SKINS = new HashMap<>();
	public static final Random RANDOM = new Random();

	static {
		SKINS.put("darkpurple", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjIzNzU1NzksInByb2ZpbGVJZCI6IjcwOTU2NDU0NTJkOTRiYTI5YzcwZDFmYTY3YjhkYTQyIiwicHJvZmlsZU5hbWUiOiJIaWRkdXMiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU4MjZjMzc1ZTJmOTZlMmQ4ZDc5NTg4ODFhZDM3NGU0ZGVmODgwYTdkNzU5MzNiOTU3NDRlYTgzYzg0ZWUwYmUifX19",
				"e/6WcAPsrXOg1ExvgONRf6NfCd2kzx9fhSp2AX1O5m6LGgYApl+G5905sUJobU1KJEJCwyLomSstWCD7MKzi1wt/UhnMkok7dTakaH/i8mvC9SgazdM0gzOIjR1Ee+KVrImi4/owgFSTJZnFIueQucjOma8jGWWJXj2oyYCzqJjkYVOTKM3zy4toLEZ0Xw6Tz6c09KES9J+ymZlENU85JPvtCB4TJZzW+C6vSxdokMydFnhe7xymr/ui7lf3F7pGhIGxEi8aqnvRnjgKgEr/5w6ccLtTaUTMQdEcdz8SzL1ClJfzPBrupaY3vtyIuZbvadyClCqZQ978neHKDOtMXEaw81AcIO9Hk9Az+DV/XypDgTZmFYcam6ppSEz8OwuQxm/kVs4n03o9xfLNqm/Da+j/WmkwOxn9xwauv+/gB1FI0ziHOUpkUGjvSsVocEWQ8lJXMWSE12i5VAjp5J2Y/+O340llQxSzXgt3BEko1BFWJuTfg9DRQ7AMDX2JG5q/U1UdwASdwQTSTlVQIaxPV/0nO7cD82NaUxuYPVsX8RUHouwV5ZoEKvrg1AoF99kA73ey2dcgLspDOFlPJprkbDM+1i639d8ucxdvKgvRlI4qy4GD7hCUn4o/hSZbFfThmTeaYT6smEtOyr9KheH1TLiVbnM9NqEXtTJwkgDasUc="));
		SKINS.put("lightpurple", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjI4ODU1NDYsInByb2ZpbGVJZCI6IjIzZjFhNTlmNDY5YjQzZGRiZGI1MzdiZmVjMTA0NzFmIiwicHJvZmlsZU5hbWUiOiIyODA3Iiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNmJjYTE2MGZlN2ZlNTdkYWUzMTM1Y2I4YzE4N2Q1YjhmZTU0ZmExMWZiNzYwOTI0YzZhZGU5OTFkMzc4In19fQ==",
				"I7qqH3HAqf6sC+zWqvUWrhYjaMMjH8TqM54+FAcbHg6LpRhkDRo9gtWIoK/SkOtd4XCWNR8+WVKt+urpZv/NUxvU8v0th+FI+nnZFsQuecoyzc6m6/dEUAS5r6Eqau0nAZ4YCXGmrdf2Qyy8/g17rglc23/n+IDLQmC0CM4lHhTGlAn9M4qIBLFXB4OeCejWOnW4GmbIqwW0V31G3JxF3b0XfhRmKvdVRKfREO4ghmY29ODC48wD/JlBMVQR6pQSZizH9xkW616MVJ7CkOx/GL/iqXw6vSNmAEGe/r45b2m4VfxVvKnT1y8Dft7+ZM8k+Y4PWR/oMy4Rq+4h/hCtwSUAxU1uFqEdLowmUPk143PoBaPxhWc07aF3xGv8vcwGeXxfH3DGcqF1hFAZyzQV4PZZELTuPZRcR7pKHc738GD2kxIZuSDKehrPt0/8WaRVjsgiJlJek5+DArKvEY8LxOFGnB9xLJAMWyuZ1/uN49JdUi8J3kRcSidu+99EINWDrS575Dmjda8lDgjZK7mabFgq8il6i6NDX2VL8qtopup3sS0eCrDDbC9cMjtR2QRiTa/Ev22j/mQHvCP8ApW7EHWje9d9zkEYFrMeUSs2qS8nOJ2DbgQSl8wAJIFszrCJv0f+UVswatVAl6h0y6XHXl1M12N9wC5YOxm7xH1r4l4="));
		SKINS.put("brown", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjI5MzMzMjUsInByb2ZpbGVJZCI6IjBiZTU2MmUxNzIyODQ3YmQ5MDY3MWYxNzNjNjA5NmNhIiwicHJvZmlsZU5hbWUiOiJ4Y29vbHgzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9mNDMxNjU0OTNmOWZkNGNmYjI0OGUxZTc0NjIyYmRmNGMxMzQ0MGRjZmYzNzI2NjllZjkyZDNlZTIxOTI2NzEifX19",
				"HVaiHl/iPIp/fQqwe6BvrAhKe8+IjR4a1J+zyWZyGvfg3azkzb6PDrENH2d3stVvddG6I8Lx6X27S1NiRe9u7rkc9ykyC0bgNJZ2bFwPESPUX7VZks+Ps7n/1I6FzFZRMuDgnh7Iy4pIr8//5m2VJwmssj+bUb5qCGN/l+QWA0FlAWL/mksX7o3DshNbaa+Nak/KAxBX/Cu6pIYTZ8QRFHQ2tQ+0lMzGrbLS0oiOoz1bzZUJZH7nJ2bz7+3FOtTv12qTVL+d2zZSANrgqo/b1VC5qxgKO3p/ZsEEQhn2LI/eln8+BPNbCql1SXXMhEG5dfMjC8e917a0BghjGU/SzYQPx7mayeBEvVJr4+E5pvEpbYDVl761eQMabu0fwAfwfp9cosZJdTVeXWjxDw0CveBRTqBI39Vc4ELN9AvLzKwkdE3TjrEI0Q6cdC8kblksb5aKIOgJcL3e6P+O0df7fzZh1kYlFLamdVS9q1UVBtDmF0v8DwG/4Rh4vQu7VZZFSndWept3c0qdDbQfMLicV7bBgrH1nyuLEK+YDnJaPyXR+ihWVdMY4tR+gfKY9qV6GDWL1GVbfTqrvJzlC7y/Wtqh5RZkOAUAH8wOtYc9fqSNKXdLH214NeCEHBae7Nx/DTqfBLCoM++3p2L71B/WSMjQuarCzA8XcFbM7G2IoZI="));
		SKINS.put("darkblue", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjMwMTg0ODIsInByb2ZpbGVJZCI6ImE5MGI4MmIwNzE4NTQ0ZjU5YmE1MTZkMGY2Nzk2NDkwIiwicHJvZmlsZU5hbWUiOiJJbUZhdFRCSCIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGYyYTlhMzk5MmZiMmRiYzEwNzJiZWRkZjBmYmYyZjljNjVhOWQ2MTE0NmZhNjE3YTExN2Q4NzdlMDQ3In19fQ==",
				"KmLJ6EN7WMiKQzWaTnIsGldD6VQPKBqMb34cgmiFyZfcnzqGINCMBhxwGinxTFffgMiYNKoABzNkmf2ihqgBgM2V8XadeL49DX8ImNFUVkZCoAS2wfTRt1BricKO6Y+qYeyoUOe+vp+5X/MNUTZxFxbT97et5Qzg/H5DwiPovNlUZSxf2OLmTLq4lh0xPdgMtw4DpYgfciXJWFVcjAMWQVdAkwexsKgthKw3I39mq7oLTJjSuJQheYVV+dWlnaZQ+vYq+xzxO0mVwjdvbJoA5KS240loGjKwh97Z7teQBx2gdcY3xMeKBRg6vkUyJ7cDEHK8zLZ+NT6ybfREdEfUtNAcHrvXeeWH4OIFSSvxKtk/nN7VlXF6SoEJF6JPN6pxoJN8euYfm86hDuWJRCJXEelDjTXXG21qT0jOW9Y+qljTkwfq1vx1iR8oAPzh7B7GKblv8PUdkIzTQYYX9aCjVERazfJKWo1E5Gyz7+v4De2+FcXN6nndIuAm7vOu+HOHVbtS/SifQSRu5AW4WutFtBnf7bg9HMnMLIeHJ55iKKG8CBFtgFl506F+2kf5KloF6YO+XxqVjz6EH1L9YDAKm2wqbXhIaGULQaT/YWPdE/fk3PnCOIiUTkglymjOoodcVkd03RY6no1JJFNuVt3kPiRJ9Cr/2o7Jb0+9aZ9pRHA="));
		SKINS.put("gray", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjMwOTgwNDQsInByb2ZpbGVJZCI6ImRhNzQ2NWVkMjljYjRkZTA5MzRkOTIwMTc0NDkxMzU1IiwicHJvZmlsZU5hbWUiOiJEYW5jaW5nRG9nZ29fIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yYTYxOTNjNDk2NzRkZTFiMWE0YjkzNjNjZWI5M2E1NDI1N2FjYzQ4M2QxZTM4YjJjNWExZTQ4ZWVmMDMwNDAifX19",
				"H7AmNHIHVeNdFMXBBKqrn2lLSkKHcUilVho6CKoSqnkuWoQG2nKokhKOWJ7yu3yK0AJFIpq3Vv9ZhZILeBBO3vcVIZSbpJGTGYflZJFwW0c0G7rj+sELavRIE2+JLFi6qES/cthjQ2ALSyD3du+8ElYkfeBEyEQZAKA4q4bK40eNBpNLFVeNVha49f8CjJmTB/bdSPJ2s6pSUSdUOu58CPtzwgQXCxPdUd81SJMLpdsSm/8wdlUIguB9mEj3nv4nq1x+f7+x8Pq+bPl+V3rNGVoVOyhxHmL2+2wqtcVkDhjfd85MSEt6phLSaB5sJGQyu4hgFxdgvfIsORuDVrUOvxLqsPWWo3Q8LnTWB/dw+dqXUCSp+0f9K2ZncByqgRXfsBq6gQcANqJQ/6wFh4octluWtU7YLKLKlVT9ZjuzriCmDJLPmYkSj58mV8k0uEEgja+Va6JI9Wpy5cT75R7P+3hsBCcE6LqBQm0NyxSpgUrGSfGvVwuKRhgGsGOGIUbbXuZ+B+VjJAqwd1JlK9ni/626E+3EFJsl4rsEmKHFTU5kd/yXInJvUK8fTijmRsFYI8niDre7ID80NOcVO/Gw5ZH5PxQltbN7K9MflkLXD0p0IHSp+MVgpnw8cahwfNCzRKXjTOFBUboQjMaCemobUwuOltindmGyyCVx1MFAbBE="));
		SKINS.put("yellow", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjMxNjU1ODMsInByb2ZpbGVJZCI6ImQxY2VjOWFkMWRhODQxNzliMWU1NjA0ZjcyYmZiMjI2IiwicHJvZmlsZU5hbWUiOiJydXRnZXI0NjUiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2E3Yzk5Yzc3OWQ1OTA3ZGMxMzAyMTg1ODRjZjQwNDczZTY3MDE0MWRiZWU4NmUwMmQ2MWNjYTJkN2Y1ZSJ9fX0=",
				"T1hBuBv5n7wwGYPG5A3uRKQEc9ru3V1aFpD0LzU4d/YISDdAxDlg8zLnjdj89PeKZgpT64mOSF3ZdPr9OqzOwRjWXduofXjxRDeFxPkTg13T+KIeUwmueTidvEz8tpnbDHSQN8MsqzOVFv/LFmQ+DdzOPGyvvSJs1Lr7xsGVdrhJethqH+aC5mX4O6cuedWg8QC+hWNlS6QGV7a9gMbUX4JPWDPpbM6qU0R2kxNM9w7wn8E2yquzCt2n7eqAO9S8y1b7wt/h5FgRCRfeEjdKlJLCXeIVjPLk43elpq4Ef1TeLD0fnA0bCGcrJ00mtBc+3904tj1TDNbEDivJtE1/umu3BJM35L+Gib66HPPYrmIGOsXd9PfV85b+f5d0Jfe9q3OQA7Ng/1tZUBRS7Cd0fRaqJpf3aQOjxsuXHbvroiAeoyrK2ePZ3oOPsPXCp3D86PreZWjkMmq7KJMrRDMfkP2Yk9NQH/2hvm13TaJgKloiTviupJLuM5fMol1Ica5K35nXPn8mp2+9EVlf35BrTcq+i/lQ70nQwybb3ILvqNbqpMrMtYizZUCaJdN5QG6a6lVtyoBx5gad3iKdgoSY/8lUxRDvN7mrFiCghQIXkLeOCy3bnBW/HoziA13Y2liCfYgzULtT9Ow1g/BJQ+Q+lfkTMKFvrdvlVMb/3Jy7LMg="));
		SKINS.put("green", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjMzMDYzNzgsInByb2ZpbGVJZCI6IjdjZjc2MTFkYmY2YjQxOWRiNjlkMmQzY2Q4NzUxZjRjIiwicHJvZmlsZU5hbWUiOiJrYXJldGg5OTkiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2FkNDQ0YWQxYmFkZWFhNmMzYTRhNmJjYTIwOWU3ZDc4MWZhNDNhMWJjM2VlMTJhNzhkMzhjNTAxZGM4MWIifX19",
				"PXcMwZlw7Vx80FRb3075wTu4plkT2iV6zfmqIeeUwR5WPTNoYEIY9ThqzYZZdy0WQYuA/SXP/728cHudBrimKfVJOGx+ZKy6ed8Irc6eEN9k/yXXma3kh4TEFgGBg4+Ymcw/e+7nR5s3ayBbOVCL2Kldtyu/iEASr1XMjF4++IkVVDcrIGwr9az85jXeBwEFMpPKIUGgyj5jNzYMS1vqBzAFll9z9gfKjH7g7zTl8+3VJPdBw9MJYc9WDxtu7KEwy/ynzHWQ7q92Wg3cQcKNjnJbtbSCL4qFSYLIiSGqoKXKlxqPTr1xj2aVDrx35JyAiMH+I9GOP/bdmVInsHQ9UC8bAnX3QxoDOuDD8LLFVZq3eOjJbeYKVgsFXDrx0T9evak9VDM9IYpUfB9Y0h+qicou53jDFggHwacQdem0VdDMdFiAfOzDwOB6wyGtE34VtCOTvictHkmbwJyL4DdX4DZZjxK/R8Qt5uSMqdmY/B01KMnbFvGI9w3nyIM5o+eFEdUq0eBA3R8QH6sIqFI2CIYiQYbih7uEcLzoY4J6AP6da92BGlXow3E7+RRdVShAEz1K+d1j+lkXw4JoUKfxcn2kB6CjQqAF1grWvhEFvoWHb4SlwqhUgU02Iu3sXCujgpzugFpbbPY1SzTVEf8cvIPeZLZdT8/++ou4Yvtfppk="));
		SKINS.put("red", new Property("textures", "eyJ0aW1lc3RhbXAiOjE1MjI1MjMzNTU5NTMsInByb2ZpbGVJZCI6IjVjN2ZiNzhhMmQxMzQ5NTZhNWE1M2EyNGQ5NWY1YjRmIiwicHJvZmlsZU5hbWUiOiJQZWFyc29uSW5tYW4iLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2MzZmQzYTFjNzJjMWViNTAxZGFkZTBkZWJiNDY4NGUyZjA4Y2M4ZDU2OTgzM2IzNzBlNWE4NjY1YzVlZTdmOSJ9fX0=",
				"uSauHylwZib1b1I6PO6roBWRNfPUMvKqcfWNQRTHs5HH/rLuilLb/aCrd7vOgH8JOCSIXL/nNA256l/RVCXC0uq89TnWFHyobGk32UR8xoZ8jTInW3QuAT+tjHSp8Jcia6ISW1kk9w07yKifZgRkS1AZmqt5JnGdV49c4vwtkR+VvTWTDIKr5y9Q+elDV33hor7M/j9MdhiLEkvxXc+Y34/u7Kqp9qCGVGkVIfI827zF+30z7m817D8E7j561Tw4K6MtWz+l8U28VmwGI3nM8vpN0DGfIU8nDpmYdSFnNaqcYgpV4+FdeXDXHua0h2oWYByI/jQwXGeYKKE/7dKnqkwJVqLNBXbVQCIyAoaPAyACezERH4wTlmC7ku3ysN9iYsBJ3c0YDs34MZCEVrynAgLIenDQrXij7EHkETGFiTmL8x80rdlMnyurhWzbDJXOmkvK0ACstamimxJMrzVDuxjdIAlnPa70qx9hTC+urTTUhnRB64pOxQol0CleRPJLPO1IU4EspjC0wmL1jiv6BQR5zHJDlCC2nj6sECS/9XtsjKah69AA3J9z+Ke/jdqf36INcj7CH6Fc05SnXmGT5WR1sBH1gf3PP4AQAHb+2RNfEq6RmywEydM1I9IDgMSBZfS2P3zKh4K0h0cEGgMs7BaRe9SHKsEW/xYAnEW0sM4="));
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		SKINS.clear();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		randomSkin(event.getPlayer());
	}

	//https://www.spigotmc.org/threads/changing-skin-with-protocollib.190621/page-2
	private static void randomSkin(Player player) {
		List<String> keys = new ArrayList<String>(SKINS.keySet());
		String randomKey = keys.get(RANDOM.nextInt(keys.size()) );
		Property newSkin = SKINS.get(randomKey);

		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if (player == onlinePlayer) {
				continue;
			}
			
			//REMOVES THE PLAYER
			((CraftPlayer)onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, ((CraftPlayer)player).getHandle()));

			//CHANGES THE PLAYER'S GAME PROFILE
			GameProfile gp = ((CraftPlayer)player).getProfile();
			gp.getProperties().removeAll("textures");
			gp.getProperties().put("textures", newSkin);

			//ADDS THE PLAYER
			((CraftPlayer)onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer)player).getHandle()));
			((CraftPlayer)onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(player.getEntityId()));
			((CraftPlayer)onlinePlayer).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)player).getHandle()));
		}
	}

}
