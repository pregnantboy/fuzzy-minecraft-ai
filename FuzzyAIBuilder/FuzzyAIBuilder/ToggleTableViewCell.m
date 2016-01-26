//
//  ToggleTableViewCell.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 25/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "ToggleTableViewCell.h"
#import "MainMenuViewController.h"

@implementation ToggleTableViewCell

- (void)drawRect:(NSRect)dirtyRect {
    [super drawRect:dirtyRect];
}

- (void)setMode:(NSInteger) mode {
    self.currIndex = 0;
    switch (mode){
        case 0:
            self.type = 0;
            self.operators = [[NSArray alloc] initWithObjects:@"IF", nil];
            break;
        case 1:
            self.type = 1;
            self.operators = [[NSArray alloc] initWithObjects:@
                         "AND", @"OR", nil];
            break;
        default:
            self.type = 2;
            self.operators = [[NSArray alloc] initWithObjects:@
                         "IS", @"IS NOT", nil];
    }
    
    [self updateButtonString];
    
}

- (void)setString:(NSString *)string {
    self.currIndex = [self.operators indexOfObject:string];
    [self updateButtonString];
}

- (void) updateButtonString {
    if (self.type == 0) {
         [MainMenuViewController addWhiteMenuText:self.toggleButton withSize:30 withText:[self getString]];
        NSImage  *newImg = [NSImage imageNamed:@"arrow right"];
        [self.toggleButton setImage:newImg];
    } else {
        [MainMenuViewController addWhiteMenuText:self.toggleButton withSize:22 withText:[self getString]];
        NSImage  *newImg = [NSImage imageNamed:@"oval2"];
        [self.toggleButton setImage:newImg];
    }
}

- (NSString *)getString {
    return [self.operators objectAtIndex:self.currIndex];
}

- (IBAction)onButtonPressed:(id)sender {
    self.currIndex = [self.operators indexOfObject:self.toggleButton.title];
    self.currIndex++;
    self.currIndex = self.currIndex % ([self.operators count]);
    [self updateButtonString];
    
}


@end
