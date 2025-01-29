# Save
## Entrypoint
The `save_init` entrypoint executes the `init(ModContainer mod);` function in your SaveModInit implemented class, when specified in your quilt.json entrypoints.

## Events
### Tick
You can use Save to tick by adding a register to your mod initializer.  
`TickEvents.register(TickEvents.Tick, String, Eventable);`
`TickEvents.register(TickEvents.Tick, String, Tickable.World);`

TickEvents.Tick has the following options:  
- `TickEvents.Tick.START`  
  - Executes at the start of the tick.  
  - Can be registered with Eventable.  
- `TickEvents.Tick.END`  
  - Executes at the end of the tick.  
  - Can be registered with Eventable.  
- `TickEvents.Tick.START_WORLD`  
  - Executes at the start of the world tick.  
  - Can be registered with Eventable, or Tickable.World.  
- `TickEvents.Tick.END_WORLD`  
  - Executes at the end of the world tick.  
  - Can be registered with Eventable, or Tickable.World.  

String is a unique identifier for your registered event.  
I suggest something like `modId_descriptionOfEvent`.

Eventable is our own custom runnable for Events (`(client) -> {}`).
Tickable.World is our own custom runnable for Events (`(client, world) -> {}`).

**Example:**  
By adding the following to your mod initializer, the first slot of your inventory will be set to 64 bread, at the start of the world tick.  
```
TickEvents.register(TickEvents.Tick.START_WORLD, "example_setItem", (client, world) -> {
			((PlayerEntity)world.f_6053391).inventory.inventorySlots[0] = new ItemStack(297, 64);
		});
```

### Rendering
You can use Save to render by adding a register to your mod initializer.  
`RenderEvents.register(RenderEvents.Render, String, Renderable);`  
`RenderEvents.register(RenderEvents.Render, String, Eventable);`

RenderEvents.Render has the following options:
- `RenderEvents.Render.AFTER_GAME_GUI`
  - Executes after rendering the gui, you have to be in a world.
- `RenderEvents.Render.END`
  - Executes after rendering everything, including screens, even if you aren't in a world.

String is a unique identifier for your registered event.  
I suggest something like `modId_descriptionOfEvent`.

Renderable is our own custom runnable for Events (`(client, tickDelta) -> {}`).
Eventable is our own custom runnable for Events (`(client) -> {}`).

**Example:**  
By adding the following to your mod initializer, 'example text' would display over the center of the screen, when in a world.
```
RenderEvents.register(RenderEvents.Render.AFTER_GAME_GUI, "example_exampleText", (client) -> {
			int width = client.f_0545414 * 240 / client.f_5990000;
			int height = client.f_5990000 * 240 / client.f_5990000;
			String text = "example text";
			client.f_0426313.drawWithShadow(text, (width / 2) - (client.f_0426313.getWidth(text) / 2), height / 2, 16755200);
		});
```