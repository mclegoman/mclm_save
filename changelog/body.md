*Please make sure you use Quilt Loader and Minecraft inf-20100325-2!*

## Changes  
- Ported to `inf-20100325-2`.  
- Added `SaveModInit` (`mclm_save_init`) Entrypoint.  
- Added `TickEvents` registry.  
  - You can register a runnable to be executed at the start or end of the client tick.
    - `inf-20100325-2` doesn't have a server, so only client ticks are available.  
```
TickEvents.register(Tick.Start, () -> {
  System.out.println("This line was printed at the start of the client tick!");
});
```
```
TickEvents.register(Tick.End, () -> {
  System.out.println("This line was printed at the end of the client tick!");
});
```
- Updated `InfoScreen` to have a `renderModInfo()` function.  
  - Added a new `SaveInfoScreen` class that extends `InfoScreen` and renders the mod version when running a development build.  
- Moved `ClientData.minecraft` to `Resources.minecraft`.  