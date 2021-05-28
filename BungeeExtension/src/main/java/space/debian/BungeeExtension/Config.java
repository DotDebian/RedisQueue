package space.debian.BungeeExtension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class Config {

    public Boolean isHub = false;
    public String redisHost = "127.0.0.1";
    public Integer redisPort = 6379;
    public String redisPassword = "";
    public String serverId = "server";

}
