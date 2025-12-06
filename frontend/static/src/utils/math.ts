const rotate = (x: number, y: number, theta: number): {x: number, y: number} => {
    /*
        | cos(t) -sin(t) | * | x | = | x*cos(t) - y*sin(t) |
        | sin(t)  cos(t) |   | y |   | x*sin(t) + y*cos(t) |
    */
    return {
        x: x*Math.cos(theta) - y*Math.sin(theta),
        y: x*Math.sin(theta) + y*Math.cos(theta)
    }
}


const degrees_to_radians = (degrees: number): number => { 
    return degrees * (Math.PI/180);
}

export {rotate, degrees_to_radians};