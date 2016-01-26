//
//  RulePopUpButton.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RulePopUpButton.h"

@implementation RulePopUpButton

- (void)drawRect:(NSRect)dirtyRect {
//    NSImage *background = [NSImage imageNamed:@"rectangle3"];
//    [background drawInRect:dirtyRect];
    [[NSColor clearColor] setFill];
    
    NSRectFill(dirtyRect);
    [super drawRect:dirtyRect];

    CGFloat iconSize = 30.0;
    CGFloat iconYLoc = (dirtyRect.size.height - iconSize) / 2.0;
    CGFloat iconXLoc = (dirtyRect.size.width - (iconSize));
    
    CGRect triRect = {iconXLoc, iconYLoc, iconSize, iconSize};
    NSImage *arrowdown = [NSImage imageNamed:@"chevrondownwithblackshadow"];
    [arrowdown drawInRect:triRect];
    
}

@end
