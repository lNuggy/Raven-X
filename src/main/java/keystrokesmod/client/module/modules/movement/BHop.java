package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;

public class BHop extends Module {
   public static SliderSetting a;
   public static TickSetting b;
   public static TickSetting e;
   public static TickSetting c;
   public static TickSetting f;
   public static SliderSetting d;
   private final double bspd = 0.0025D;

   public BHop() {
      super("Bhop", ModuleCategory.movement);
      this.registerSetting(a = new SliderSetting("Speed", 2.0D, 1.0D, 15.0D, 0.2D));
      this.registerSetting(b = new TickSetting("Sprint", true));
      this.registerSetting(c = new TickSetting("Verus", false));
      this.registerSetting(f = new TickSetting("Strafeless", false));
      this.registerSetting(e = new TickSetting("Custom Jump", false));
      this.registerSetting(d = new SliderSetting("Jump Height", 1.0D, 0.1D, 3.0D, 0.1D));
   }

   public void update() {
      Module fly = Raven.moduleManager.getModuleByClass(Fly.class);
      if (c.isToggled()) {
         if (Utils.Player.isMoving()) {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               if (f.isToggled()) {
                  Utils.Player.strafe(0);
               }
               if (!f.isToggled()) {
                 Utils.Player.strafe(0.48F);
               }
            }
         }
      }

      if (!c.isToggled()) {
         if (fly != null && !fly.isEnabled() && Utils.Player.isMoving() && !mc.thePlayer.isInWater()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
            mc.thePlayer.noClip = true;
            if (mc.thePlayer.onGround) {
               if (d.getInput() == 1) {
                  mc.thePlayer.jump();
               }
            }

            if (mc.thePlayer.onGround) {
               if (d.getInput() != 1) {
                  mc.thePlayer.motionY = d.getInput();
               }
            }

            mc.thePlayer.setSprinting(b.isToggled());
            double spd = 0.0025D * a.getInput();
            double m = (float)(Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
            if (f.isToggled()) {
               Utils.Player.strafe(0);
            }
            if (!f.isToggled()) {
               Utils.Player.strafe(m);
            }
         }
      }

   }
}
